import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPreguntas } from 'app/shared/model/preguntas.model';

@Component({
  selector: 'jhi-preguntas-detail',
  templateUrl: './preguntas-detail.component.html'
})
export class PreguntasDetailComponent implements OnInit {
  preguntas: IPreguntas;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ preguntas }) => {
      this.preguntas = preguntas;
    });
  }

  previousState() {
    window.history.back();
  }
}
