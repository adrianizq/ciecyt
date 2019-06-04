import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIntegrantesProyecto, IntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { IntegrantesProyectoService } from './integrantes-proyecto.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';
import { IUser, UserService } from 'app/core';
import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';
import { RolesUsuarioProyectoService } from 'app/entities/roles-usuario-proyecto';

@Component({
  selector: 'jhi-integrantes-proyecto-update',
  templateUrl: './integrantes-proyecto-update.component.html'
})
export class IntegrantesProyectoUpdateComponent implements OnInit {
  integrantesProyecto: IIntegrantesProyecto;
  isSaving: boolean;

  proyectos: IProyecto[];

  users: IUser[];

  rolesusuarioproyectos: IRolesUsuarioProyecto[];

  editForm = this.fb.group({
    id: [],
    proyecto: [],
    user: [],
    rolesUsuarioProyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected integrantesProyectoService: IntegrantesProyectoService,
    protected proyectoService: ProyectoService,
    protected userService: UserService,
    protected rolesUsuarioProyectoService: RolesUsuarioProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ integrantesProyecto }) => {
      this.updateForm(integrantesProyecto);
      this.integrantesProyecto = integrantesProyecto;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.rolesUsuarioProyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRolesUsuarioProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRolesUsuarioProyecto[]>) => response.body)
      )
      .subscribe(
        (res: IRolesUsuarioProyecto[]) => (this.rolesusuarioproyectos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(integrantesProyecto: IIntegrantesProyecto) {
    this.editForm.patchValue({
      id: integrantesProyecto.id,
      proyecto: integrantesProyecto.proyecto,
      user: integrantesProyecto.user,
      rolesUsuarioProyecto: integrantesProyecto.rolesUsuarioProyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const integrantesProyecto = this.createFromForm();
    if (integrantesProyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.integrantesProyectoService.update(integrantesProyecto));
    } else {
      this.subscribeToSaveResponse(this.integrantesProyectoService.create(integrantesProyecto));
    }
  }

  private createFromForm(): IIntegrantesProyecto {
    const entity = {
      ...new IntegrantesProyecto(),
      id: this.editForm.get(['id']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      user: this.editForm.get(['user']).value,
      rolesUsuarioProyecto: this.editForm.get(['rolesUsuarioProyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntegrantesProyecto>>) {
    result.subscribe((res: HttpResponse<IIntegrantesProyecto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProyectoById(index: number, item: IProyecto) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackRolesUsuarioProyectoById(index: number, item: IRolesUsuarioProyecto) {
    return item.id;
  }
}
