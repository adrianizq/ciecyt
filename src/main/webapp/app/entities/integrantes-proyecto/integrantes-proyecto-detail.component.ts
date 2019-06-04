import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

@Component({
  selector: 'jhi-integrantes-proyecto-detail',
  templateUrl: './integrantes-proyecto-detail.component.html'
})
export class IntegrantesProyectoDetailComponent implements OnInit {
  integrantesProyecto: IIntegrantesProyecto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ integrantesProyecto }) => {
      this.integrantesProyecto = integrantesProyecto;
    });
  }

  previousState() {
    window.history.back();
  }
}
