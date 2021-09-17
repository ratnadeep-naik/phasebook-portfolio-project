import { Component, OnInit } from '@angular/core';
import { AuthGuardService } from "../auth-guard.service";
import { ActivatedRoute, Params, Router } from '@angular/router';


@Component({
  selector: 'app-user-account-container',
  templateUrl: './user-account-container.component.html',
  styleUrls: ['./user-account-container.component.css']
})
export class UserAccountContainerComponent implements OnInit {

  own1:boolean=false;
  email1:string=""; 
constructor(public authGuardService: AuthGuardService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {

    if(this.router.url=="/"){
      this.own1=true;
      this.email1="";
      
    }else if(this.route.snapshot.params['email']){
      this.own1=false;
      this.email1=this.route.snapshot.params['email'];
    }
  }
}