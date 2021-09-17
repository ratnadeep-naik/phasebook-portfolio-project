import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-group-account-detail',
  templateUrl: './group-account-detail.component.html',
  styleUrls: ['./group-account-detail.component.css', './group-account-detail.form.component .css']
})
export class GroupAccountDetailComponent implements OnInit {

  showEditForm: boolean = false;


  constructor() { }

  ngOnInit(): void {
  }

}
