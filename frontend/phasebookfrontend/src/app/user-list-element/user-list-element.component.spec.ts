import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListElementComponent } from './user-list-element.component';

describe('UserListElementComponent', () => {
  let component: UserListElementComponent;
  let fixture: ComponentFixture<UserListElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserListElementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
