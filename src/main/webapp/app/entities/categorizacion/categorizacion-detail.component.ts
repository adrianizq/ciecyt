import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorizacion } from 'app/shared/model/categorizacion.model';

@Component({
  selector: 'jhi-categorizacion-detail',
  templateUrl: './categorizacion-detail.component.html'
})
export class CategorizacionDetailComponent implements OnInit {
  categorizacion: ICategorizacion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categorizacion }) => {
      this.categorizacion = categorizacion;
    });
  }

  previousState() {
    window.history.back();
  }
}
