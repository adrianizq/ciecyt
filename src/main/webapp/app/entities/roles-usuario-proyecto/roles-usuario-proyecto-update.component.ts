import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRolesUsuarioProyecto, RolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';
import { RolesUsuarioProyectoService } from './roles-usuario-proyecto.service';

@Component({
  selector: 'jhi-roles-usuario-proyecto-update',
  templateUrl: './roles-usuario-proyecto-update.component.html'
})
export class RolesUsuarioProyectoUpdateComponent implements OnInit {
  rolesUsuarioProyecto: IRolesUsuarioProyecto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    rol: [],
    descripcion: []
  });

  constructor(
    protected rolesUsuarioProyectoService: RolesUsuarioProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rolesUsuarioProyecto }) => {
      this.updateForm(rolesUsuarioProyecto);
      this.rolesUsuarioProyecto = rolesUsuarioProyecto;
    });
  }

  updateForm(rolesUsuarioProyecto: IRolesUsuarioProyecto) {
    this.editForm.patchValue({
      id: rolesUsuarioProyecto.id,
      rol: rolesUsuarioProyecto.rol,
      descripcion: rolesUsuarioProyecto.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rolesUsuarioProyecto = this.createFromForm();
    if (rolesUsuarioProyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.rolesUsuarioProyectoService.update(rolesUsuarioProyecto));
    } else {
      this.subscribeToSaveResponse(this.rolesUsuarioProyectoService.create(rolesUsuarioProyecto));
    }
  }

  private createFromForm(): IRolesUsuarioProyecto {
    const entity = {
      ...new RolesUsuarioProyecto(),
      id: this.editForm.get(['id']).value,
      rol: this.editForm.get(['rol']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRolesUsuarioProyecto>>) {
    result.subscribe((res: HttpResponse<IRolesUsuarioProyecto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
