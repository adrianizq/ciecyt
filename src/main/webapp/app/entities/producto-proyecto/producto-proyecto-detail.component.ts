import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';

@Component({
  selector: 'jhi-producto-proyecto-detail',
  templateUrl: './producto-proyecto-detail.component.html'
})
export class ProductoProyectoDetailComponent implements OnInit {
  productoProyecto: IProductoProyecto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productoProyecto }) => {
      this.productoProyecto = productoProyecto;
    });
  }

  previousState() {
    window.history.back();
  }
}
