import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DividentComponent } from './divident.component';

describe('DividentComponent', () => {
  let component: DividentComponent;
  let fixture: ComponentFixture<DividentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DividentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DividentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
