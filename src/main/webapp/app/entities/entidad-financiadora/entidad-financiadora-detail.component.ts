import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

@Component({
  selector: 'jhi-entidad-financiadora-detail',
  templateUrl: './entidad-financiadora-detail.component.html'
})
export class EntidadFinanciadoraDetailComponent implements OnInit {
  entidadFinanciadora: IEntidadFinanciadora;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ entidadFinanciadora }) => {
      this.entidadFinanciadora = entidadFinanciadora;
    });
  }

  previousState() {
    window.history.back();
  }
}
