import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElementos } from 'app/shared/model/elementos.model';

@Component({
  selector: 'jhi-elementos-detail',
  templateUrl: './elementos-detail.component.html'
})
export class ElementosDetailComponent implements OnInit {
  elementos: IElementos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elementos }) => {
      this.elementos = elementos;
    });
  }

  previousState() {
    window.history.back();
  }
}
