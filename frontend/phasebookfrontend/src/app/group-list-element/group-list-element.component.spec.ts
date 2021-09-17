import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupListElementComponent } from './group-list-element.component';

describe('GroupListElementComponent', () => {
  let component: GroupListElementComponent;
  let fixture: ComponentFixture<GroupListElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupListElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
