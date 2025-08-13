import { TestBed } from '@angular/core/testing';

import { CtService } from './ct.service';

describe('CtService', () => {
  let service: CtService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CtService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
