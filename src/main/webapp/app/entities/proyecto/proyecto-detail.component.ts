import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProyecto } from 'app/shared/model/proyecto.model';

@Component({
  selector: 'jhi-proyecto-detail',
  templateUrl: './proyecto-detail.component.html'
})
export class ProyectoDetailComponent implements OnInit {
  proyecto: IProyecto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      this.proyecto = proyecto;
    });
  }

  previousState() {
    window.history.back();
  }
}
