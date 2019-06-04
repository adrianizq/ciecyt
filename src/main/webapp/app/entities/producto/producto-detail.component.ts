import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProducto } from 'app/shared/model/producto.model';

@Component({
  selector: 'jhi-producto-detail',
  templateUrl: './producto-detail.component.html'
})
export class ProductoDetailComponent implements OnInit {
  producto: IProducto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.producto = producto;
    });
  }

  previousState() {
    window.history.back();
  }
}
