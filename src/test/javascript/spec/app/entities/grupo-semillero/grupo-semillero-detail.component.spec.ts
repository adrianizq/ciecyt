/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { GrupoSemilleroDetailComponent } from 'app/entities/grupo-semillero/grupo-semillero-detail.component';
import { GrupoSemillero } from 'app/shared/model/grupo-semillero.model';

describe('Component Tests', () => {
  describe('GrupoSemillero Management Detail Component', () => {
    let comp: GrupoSemilleroDetailComponent;
    let fixture: ComponentFixture<GrupoSemilleroDetailComponent>;
    const route = ({ data: of({ grupoSemillero: new GrupoSemillero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [GrupoSemilleroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GrupoSemilleroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoSemilleroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grupoSemillero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
