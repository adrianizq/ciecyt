import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolicitud } from 'app/shared/model/solicitud.model';

@Component({
  selector: 'jhi-solicitud-detail',
  templateUrl: './solicitud-detail.component.html'
})
export class SolicitudDetailComponent implements OnInit {
  solicitud: ISolicitud;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ solicitud }) => {
      this.solicitud = solicitud;
    });
  }

  previousState() {
    window.history.back();
  }
}
