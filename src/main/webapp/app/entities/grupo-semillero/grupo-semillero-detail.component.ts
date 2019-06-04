import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';

@Component({
  selector: 'jhi-grupo-semillero-detail',
  templateUrl: './grupo-semillero-detail.component.html'
})
export class GrupoSemilleroDetailComponent implements OnInit {
  grupoSemillero: IGrupoSemillero;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grupoSemillero }) => {
      this.grupoSemillero = grupoSemillero;
    });
  }

  previousState() {
    window.history.back();
  }
}
