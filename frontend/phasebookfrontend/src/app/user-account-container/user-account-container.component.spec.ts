import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountContainerComponent } from './user-account-container.component';

describe('UserAccountContainerComponent', () => {
  let component: UserAccountContainerComponent;
  let fixture: ComponentFixture<UserAccountContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAccountContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAccountContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
