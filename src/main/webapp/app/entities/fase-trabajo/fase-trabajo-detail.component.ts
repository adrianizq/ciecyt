import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';

@Component({
  selector: 'jhi-fase-trabajo-detail',
  templateUrl: './fase-trabajo-detail.component.html'
})
export class FaseTrabajoDetailComponent implements OnInit {
  faseTrabajo: IFaseTrabajo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ faseTrabajo }) => {
      this.faseTrabajo = faseTrabajo;
    });
  }

  previousState() {
    window.history.back();
  }
}
