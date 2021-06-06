import { TestBed } from '@angular/core/testing';

import { IporiService } from './ipori.service';

describe('IporiService', () => {
  let service: IporiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IporiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
