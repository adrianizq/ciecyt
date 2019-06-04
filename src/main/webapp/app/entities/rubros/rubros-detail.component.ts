import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRubros } from 'app/shared/model/rubros.model';

@Component({
  selector: 'jhi-rubros-detail',
  templateUrl: './rubros-detail.component.html'
})
export class RubrosDetailComponent implements OnInit {
  rubros: IRubros;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rubros }) => {
      this.rubros = rubros;
    });
  }

  previousState() {
    window.history.back();
  }
}
