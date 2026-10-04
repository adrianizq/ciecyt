<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-ciecyt :proyectoId="$route.params.proyectoId"></menu-lateral-ciecyt>
    </div>
    <div class="col-sm-8">
      <form @submit.prevent="save()">
        <div class="row">
          <div class="col-12" v-for="(integrante, i) in integrantesProyecto" :key="i">
            <b-form-group class="mb-3"
              :label="`${labelTitulo} # ${i + 1}`"
              :label-for="`integrante-${i}`"
            >
              <b-form-select
                v-if="!integrante.esExterno"
                :id="`integrante-${i}`"
                :options="withPlaceholder(options, 'busque por nombre o cedula')"
                v-model="integrante.integranteProyectoUserId"
              ></b-form-select>
              <b-form-select
                v-if="integrante.esExterno"
                :id="`integrante-${i}`"
                :options="withPlaceholder(opcionesExternos, 'profesional externo verificado por el CIECYT')"
                v-model="integrante.integranteProyectoExternoId"
              ></b-form-select>
              <b-form-checkbox
                class="mt-2"
                v-model="integrante.esExterno"
                @change="alternarExterno(integrante)"
              >
                Documentarlo como profesional externo (sin vinculo laboral con la institucion)
              </b-form-checkbox>
            </b-form-group>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <button type="button" id="cancel" class="btn btn-secondary" v-on:click="back">
              <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver
            </button>
            <button type="button" id="save" class="btn btn-primary" v-on:click="save()" :disabled="isSaving">
              <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
            </button>
          </div>
        </div>

      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue, Hook, Prop } from 'vue-facing-decorator';

import AlertService from '@/shared/alert/alert.service';
import MenuLateralCiecyt from '@/components/ciecyt/menu_lateral_ciecyt.vue';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
import { IDocenteHabilitado } from '@/shared/model/docente-habilitado.model';
import AsesorExternoService from '@/entities/asesor-externo/asesor-externo.service';
import { IAsesorExterno } from '@/shared/model/asesor-externo.model';
import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import { withPlaceholder as withPlaceholderOptions } from '@/shared/filter/filter';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

type RoleAsignar = 'jurado' | 'asesor';

@Component({
  components: { MenuLateralCiecyt },
})
export default class AsignarIntegrantes extends Vue {
  /**
   * 'jurado' o 'asesor': parametrizado por la pagina padre.
   * Determina el titulo, el literal del rol y el endpoint del backend que devuelve
   * las designaciones actuales del proyecto.
   */
  @Prop({ default: 'jurado' })
  role: RoleAsignar;

  @Inject private proyectoService: () => ProyectoService;
  @Inject private integranteProyectoService: () => IntegranteProyectoService;
  @Inject private rolesModalidadService: () => RolesModalidadService;
  @Inject private docenteHabilitadoService: () => DocenteHabilitadoService;
  @Inject private asesorExternoService: () => AsesorExternoService;
  @Inject private alertService: () => AlertService;

  public integrantesProyecto: IIntegranteProyecto[] = [];
  public proyecto: IProyecto = new Proyecto();
  public facultadId: number = null;
  public proyId: number = null;
  public isSaving = false;
  public modalidadId: number = 0;
  public rolesModalidad: IRolesModalidad;
  public rolModalidadId: number = 0;
  public cantidadEsperada = 0;
  public options: any[] = [];
  public opcionesExternos: any[] = [];

  get rolMayusculas(): string {
    return this.role.toUpperCase();
  }

  get rolTitulo(): string {
    return this.role === 'jurado' ? 'Jurado' : 'Asesor';
  }

  get labelTitulo(): string {
    return this.rolTitulo;
  }

  public withPlaceholder(options: any[], placeholder: string): any[] {
    return withPlaceholderOptions(options, placeholder);
  }

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      (vm as any).initRelationships();
    });
  }

  mounted() {
    this.proyId = parseInt(this.$route.params.proyectoId);
    this.initRelationships();
  }

  public back() {
    this.$router.go(-1);
  }

  public async save(): Promise<void> {
    this.isSaving = true;
    try {
      for (const integrante of this.integrantesProyecto) {
        if (integrante.esExterno) {
          integrante.integranteProyectoUserId = null;
          integrante.integranteProyectoUserLogin = null;
          integrante.integranteProyectoUserFirstName = null;
          integrante.integranteProyectoUserLastName = null;
        } else {
          integrante.integranteProyectoExternoId = null;
          integrante.integranteProyectoExternoNombre = null;
        }
        const sinDesignar = integrante.esExterno
          ? integrante.integranteProyectoExternoId == null
          : integrante.integranteProyectoUserId == null;
        if (sinDesignar) {
          continue;
        }
        if (integrante.id) {
          await this.integranteProyectoService().update(integrante);
        } else {
          const param = await this.integranteProyectoService().create(integrante);
          integrante.id = param.id;
        }
      }
      this.alertService().showAlert('Las designaciones se guardaron correctamente.', 'success');
      await this.initRelationships();
    } catch (e) {
      this.alertService().showHttpError(this, e && e.response ? e.response : e);
    } finally {
      this.isSaving = false;
    }
  }

  /**
   * Los candidatos son los que la decanatura tiene habilitados como jurado o asesor en la
   * facultad del proyecto, no todos los usuarios con ese rol. El backend rechaza a quien no
   * este en ese padron, asi que ofrecer a otro solo producia un error al guardar.
   */
  async cargarHabilitados() {
    if (!this.facultadId) {
      this.options = [];
      return;
    }
    try {
      const res = await this.docenteHabilitadoService().retrieveDeFacultad(this.facultadId, this.rolMayusculas);
      const habilitados: IDocenteHabilitado[] = res.data || [];
      this.options = habilitados.map(h => {
        const u: any = h.user || {};
        const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
        const texto = nombre ? nombre + ' (' + (u.login || '') + ')' : u.login;
        return {
          value: u.id,
          text: (texto || 'Sin nombre') + ' — ' + (h.rol || ''),
          vigente: !h.fechaHasta,
        };
      });
    } catch (e) {
      this.options = [];
      if (e && e.response && e.response.status === 403) {
        this.alertService()
          .error('No tiene permiso para consultar el padron de habilitados de la facultad')
          .then(() => {});
      }
    }
  }

  /**
   * Los profesionales externos son los que el CIECYT ya verifico para esta facultad,
   * solo asi pueden designarse (Acuerdo 25, paragrafo 1 de los articulos 8 y 9).
   */
  async cargarExternos() {
    if (!this.facultadId) {
      this.opcionesExternos = [];
      return;
    }
    try {
      const res = await this.asesorExternoService().retrieveDeFacultad(this.facultadId, this.rolMayusculas);
      const externos: IAsesorExterno[] = res.data || [];
      this.opcionesExternos = externos.map(e => {
        const nombre = ((e.nombres || '') + ' ' + (e.apellidos || '')).trim();
        return {
          value: e.id,
          text: (nombre || 'Sin nombre') + ' (doc. ' + (e.numeroDocumento || '') + ')',
        };
      });
    } catch (e) {
      this.opcionesExternos = [];
      if (e && e.response && e.response.status === 403) {
        this.alertService()
          .error('No tiene permiso para consultar los profesionales externos de la facultad')
          .then(() => {});
      }
    }
  }

  /**
   * Al abrir la casilla de externo se descarta el usuario institucional seleccionado y al
   * cerrarla se descarta el externo: nunca se guardan las dos designaciones a la vez y el
   * backend lo rechaza asi llegara igualmente.
   */
  public alternarExterno(integrante: any) {
    if (integrante.esExterno) {
      integrante.integranteProyectoUserId = null;
      integrante.integranteProyectoUserLogin = null;
      integrante.integranteProyectoUserFirstName = null;
      integrante.integranteProyectoUserLastName = null;
    } else {
      integrante.integranteProyectoExternoId = null;
      integrante.integranteProyectoExternoNombre = null;
    }
  }

  /**
   * Quien ya estaba designado conserva su designacion aun si salio del padron despues, pero
   * no apareceria en la lista de candidatos y el selector lo mostraria vacio. Se agrega a
   * las opciones, marcado como no vigente, para que lo ya designado se vea y no se pierda.
   */
  public anadirDesignadosFueraDelPadron() {
    if (!this.integrantesProyecto) {
      return;
    }
    const yaEnOpciones = new Set(this.options.map(o => o.value));
    const yaExternos = new Set(this.opcionesExternos.map(o => o.value));
    for (const integrante of this.integrantesProyecto) {
      const extId = integrante.integranteProyectoExternoId;
      if (extId != null) {
        integrante.esExterno = true;
        if (!yaExternos.has(extId)) {
          this.opcionesExternos.push({
            value: extId,
            text: (integrante.integranteProyectoExternoNombre || 'Profesional externo') + ' (ya designado)',
          });
          yaExternos.add(extId);
        }
      } else {
        integrante.esExterno = false;
      }
      const u: any = (integrante as any).integranteProyectoUser || {};
      const userId = integrante.integranteProyectoUserId;
      if (userId == null || yaEnOpciones.has(userId)) {
        continue;
      }
      const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
      const texto = nombre ? nombre + ' (' + (u.login || '') + ')' : (u.login || 'Docente');
      this.options.push({
        value: userId,
        text: texto + ' (no vigente — ya designado)',
        vigente: false,
      });
      yaEnOpciones.add(userId);
    }
  }

  async initRelationships() {
    try {
      if (this.proyId == null) {
        this.proyId = parseInt(this.$route.params.proyectoId);
      }
      this.proyecto = await this.proyectoService().find(this.proyId);
      this.facultadId = this.proyecto.facultadId;
      this.modalidadId = this.proyecto.proyectoModalidadId;

      await this.cargarHabilitados();
      await this.cargarExternos();

      const resIntegrantes =
        this.role === 'jurado'
          ? await this.integranteProyectoService().retrieveJuradosProyecto(this.proyId, this.rolTitulo)
          : await this.integranteProyectoService().retrieveAsesoresProyecto(this.proyId);
      this.integrantesProyecto = resIntegrantes.data;
      this.anadirDesignadosFueraDelPadron();

      if (this.integrantesProyecto.length === 0) {
        const res = await this.rolesModalidadService().findRolModalidad(this.rolTitulo, this.modalidadId);
        this.rolesModalidad = res;
        this.cantidadEsperada = res.cantidad;
        this.rolModalidadId = res.id;
        for (let i = 0; i < this.cantidadEsperada; i++) {
          const integrante = new IntegranteProyecto();
          integrante.integranteProyectoProyectoId = this.proyId;
          integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;
          integrante.esExterno = false;
          this.integrantesProyecto.push(integrante);
        }
      }
    } catch (e) {
      // La pagina padre delega el manejo del error en el alert global.
    }
  }
}
</script>
