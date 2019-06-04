/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { SolicitudDetailComponent } from 'app/entities/solicitud/solicitud-detail.component';
import { Solicitud } from 'app/shared/model/solicitud.model';

describe('Component Tests', () => {
  describe('Solicitud Management Detail Component', () => {
    let comp: SolicitudDetailComponent;
    let fixture: ComponentFixture<SolicitudDetailComponent>;
    const route = ({ data: of({ solicitud: new Solicitud(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [SolicitudDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SolicitudDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SolicitudDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.solicitud).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
