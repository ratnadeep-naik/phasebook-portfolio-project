import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebsocketExperiment2Component } from './websocket-experiment2.component';

describe('WebsocketExperiment2Component', () => {
  let component: WebsocketExperiment2Component;
  let fixture: ComponentFixture<WebsocketExperiment2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WebsocketExperiment2Component ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WebsocketExperiment2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
