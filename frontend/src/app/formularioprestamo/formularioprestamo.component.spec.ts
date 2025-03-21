import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioprestamoComponent } from './formularioprestamo.component';

describe('FormularioprestamoComponent', () => {
  let component: FormularioprestamoComponent;
  let fixture: ComponentFixture<FormularioprestamoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioprestamoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioprestamoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
