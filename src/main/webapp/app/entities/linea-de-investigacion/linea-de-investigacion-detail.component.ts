import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

@Component({
  selector: 'jhi-linea-de-investigacion-detail',
  templateUrl: './linea-de-investigacion-detail.component.html'
})
export class LineaDeInvestigacionDetailComponent implements OnInit {
  lineaDeInvestigacion: ILineaDeInvestigacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lineaDeInvestigacion }) => {
      this.lineaDeInvestigacion = lineaDeInvestigacion;
    });
  }

  previousState() {
    window.history.back();
  }
}
