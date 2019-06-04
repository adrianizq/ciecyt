import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

@Component({
  selector: 'jhi-pregunta-respuesta-detail',
  templateUrl: './pregunta-respuesta-detail.component.html'
})
export class PreguntaRespuestaDetailComponent implements OnInit {
  preguntaRespuesta: IPreguntaRespuesta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ preguntaRespuesta }) => {
      this.preguntaRespuesta = preguntaRespuesta;
    });
  }

  previousState() {
    window.history.back();
  }
}
