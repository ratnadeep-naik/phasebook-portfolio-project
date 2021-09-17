import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupAccountContainerComponent } from './group-account-container.component';

describe('GroupAccountContainerComponent', () => {
  let component: GroupAccountContainerComponent;
  let fixture: ComponentFixture<GroupAccountContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupAccountContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupAccountContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
