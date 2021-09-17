import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebsocketExperimentComponent } from './websocket-experiment.component';

describe('WebsocketExperimentComponent', () => {
  let component: WebsocketExperimentComponent;
  let fixture: ComponentFixture<WebsocketExperimentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WebsocketExperimentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WebsocketExperimentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
