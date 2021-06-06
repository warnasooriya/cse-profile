import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IpoAndRightIssuesComponent } from './ipo-and-right-issues.component';

describe('IpoAndRightIssuesComponent', () => {
  let component: IpoAndRightIssuesComponent;
  let fixture: ComponentFixture<IpoAndRightIssuesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IpoAndRightIssuesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IpoAndRightIssuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
