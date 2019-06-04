import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

@Component({
  selector: 'jhi-presupuesto-valor-detail',
  templateUrl: './presupuesto-valor-detail.component.html'
})
export class PresupuestoValorDetailComponent implements OnInit {
  presupuestoValor: IPresupuestoValor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ presupuestoValor }) => {
      this.presupuestoValor = presupuestoValor;
    });
  }

  previousState() {
    window.history.back();
  }
}
