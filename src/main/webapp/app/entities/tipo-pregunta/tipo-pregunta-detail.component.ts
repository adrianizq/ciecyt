import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';

@Component({
  selector: 'jhi-tipo-pregunta-detail',
  templateUrl: './tipo-pregunta-detail.component.html'
})
export class TipoPreguntaDetailComponent implements OnInit {
  tipoPregunta: ITipoPregunta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoPregunta }) => {
      this.tipoPregunta = tipoPregunta;
    });
  }

  previousState() {
    window.history.back();
  }
}
