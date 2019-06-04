import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICronograma } from 'app/shared/model/cronograma.model';

@Component({
  selector: 'jhi-cronograma-detail',
  templateUrl: './cronograma-detail.component.html'
})
export class CronogramaDetailComponent implements OnInit {
  cronograma: ICronograma;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cronograma }) => {
      this.cronograma = cronograma;
    });
  }

  previousState() {
    window.history.back();
  }
}
