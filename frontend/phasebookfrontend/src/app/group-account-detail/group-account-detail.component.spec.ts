import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupAccountDetailComponent } from './group-account-detail.component';

describe('GroupAccountDetailComponent', () => {
  let component: GroupAccountDetailComponent;
  let fixture: ComponentFixture<GroupAccountDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupAccountDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupAccountDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
