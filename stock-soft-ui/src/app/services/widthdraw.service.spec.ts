import { TestBed } from '@angular/core/testing';

import { WidthdrawService } from './widthdraw.service';

describe('WidthdrawService', () => {
  let service: WidthdrawService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WidthdrawService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
