import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFichaTecnica, FichaTecnica } from 'app/shared/model/ficha-tecnica.model';
import { FichaTecnicaService } from './ficha-tecnica.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-ficha-tecnica-update',
  templateUrl: './ficha-tecnica-update.component.html'
})
export class FichaTecnicaUpdateComponent implements OnInit {
  fichaTecnica: IFichaTecnica;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    tituloProfecional: [],
    tituloPosgrado: [],
    experencia: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fichaTecnicaService: FichaTecnicaService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fichaTecnica }) => {
      this.updateForm(fichaTecnica);
      this.fichaTecnica = fichaTecnica;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fichaTecnica: IFichaTecnica) {
    this.editForm.patchValue({
      id: fichaTecnica.id,
      tituloProfecional: fichaTecnica.tituloProfecional,
      tituloPosgrado: fichaTecnica.tituloPosgrado,
      experencia: fichaTecnica.experencia,
      user: fichaTecnica.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fichaTecnica = this.createFromForm();
    if (fichaTecnica.id !== undefined) {
      this.subscribeToSaveResponse(this.fichaTecnicaService.update(fichaTecnica));
    } else {
      this.subscribeToSaveResponse(this.fichaTecnicaService.create(fichaTecnica));
    }
  }

  private createFromForm(): IFichaTecnica {
    const entity = {
      ...new FichaTecnica(),
      id: this.editForm.get(['id']).value,
      tituloProfecional: this.editForm.get(['tituloProfecional']).value,
      tituloPosgrado: this.editForm.get(['tituloPosgrado']).value,
      experencia: this.editForm.get(['experencia']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichaTecnica>>) {
    result.subscribe((res: HttpResponse<IFichaTecnica>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
