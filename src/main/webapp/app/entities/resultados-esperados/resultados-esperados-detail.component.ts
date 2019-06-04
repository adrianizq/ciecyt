import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

@Component({
  selector: 'jhi-resultados-esperados-detail',
  templateUrl: './resultados-esperados-detail.component.html'
})
export class ResultadosEsperadosDetailComponent implements OnInit {
  resultadosEsperados: IResultadosEsperados;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resultadosEsperados }) => {
      this.resultadosEsperados = resultadosEsperados;
    });
  }

  previousState() {
    window.history.back();
  }
}
