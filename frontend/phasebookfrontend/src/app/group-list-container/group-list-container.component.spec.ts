import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListContainerComponent } from './group-list-container.component';

describe('GroupListContainerComponent', () => {
  let component: GroupListContainerComponent;
  let fixture: ComponentFixture<GroupListContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupListContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupListContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
