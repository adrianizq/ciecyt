import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

@Component({
  selector: 'jhi-impactos-esperados-detail',
  templateUrl: './impactos-esperados-detail.component.html'
})
export class ImpactosEsperadosDetailComponent implements OnInit {
  impactosEsperados: IImpactosEsperados;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ impactosEsperados }) => {
      this.impactosEsperados = impactosEsperados;
    });
  }

  previousState() {
    window.history.back();
  }
}
