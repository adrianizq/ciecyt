import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductoProyecto, ProductoProyecto } from 'app/shared/model/producto-proyecto.model';
import { ProductoProyectoService } from './producto-proyecto.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-producto-proyecto-update',
  templateUrl: './producto-proyecto-update.component.html'
})
export class ProductoProyectoUpdateComponent implements OnInit {
  productoProyecto: IProductoProyecto;
  isSaving: boolean;

  proyectos: IProyecto[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    aplica: [],
    descripcion: [],
    proyecto: [],
    producto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoProyectoService: ProductoProyectoService,
    protected proyectoService: ProyectoService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productoProyecto }) => {
      this.updateForm(productoProyecto);
      this.productoProyecto = productoProyecto;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productoProyecto: IProductoProyecto) {
    this.editForm.patchValue({
      id: productoProyecto.id,
      aplica: productoProyecto.aplica,
      descripcion: productoProyecto.descripcion,
      proyecto: productoProyecto.proyecto,
      producto: productoProyecto.producto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productoProyecto = this.createFromForm();
    if (productoProyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoProyectoService.update(productoProyecto));
    } else {
      this.subscribeToSaveResponse(this.productoProyectoService.create(productoProyecto));
    }
  }

  private createFromForm(): IProductoProyecto {
    const entity = {
      ...new ProductoProyecto(),
      id: this.editForm.get(['id']).value,
      aplica: this.editForm.get(['aplica']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      producto: this.editForm.get(['producto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoProyecto>>) {
    result.subscribe((res: HttpResponse<IProductoProyecto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }
}
