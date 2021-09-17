import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebsocketExperiment3Component } from './websocket-experiment3.component';

describe('WebsocketExperiment3Component', () => {
  let component: WebsocketExperiment3Component;
  let fixture: ComponentFixture<WebsocketExperiment3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WebsocketExperiment3Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WebsocketExperiment3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
