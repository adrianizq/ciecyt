import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  producto: IProducto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    producto: []
  });

  constructor(protected productoService: ProductoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
      this.producto = producto;
    });
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      producto: producto.producto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    const entity = {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      producto: this.editForm.get(['producto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
