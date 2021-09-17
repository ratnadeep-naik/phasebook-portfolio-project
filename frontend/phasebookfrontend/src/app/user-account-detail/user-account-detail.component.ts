import { Component, OnInit, Input } from '@angular/core';
import { AuthGuardService } from "../auth-guard.service";
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClientService } from "../http-client.service";
import { FormBuilder, FormGroup } from '@angular/forms';

interface UserDetailInterface { 
	own:boolean,
	friend:boolean,
	friendRequestSentByThisUser:boolean,
	friendRequestSentToThisUser:boolean,
	email: string,
	firstName: string,
	lastName: string,
	photoUrl?: string,
	address?: string,
	phone?: string,
	gender?: string,
	jobInfo?: string,
	academicInfo?: string,
	personalInfo?: string,
	dob?:Date,
	lastAppearance?:Date
} 


@Component({
  selector: 'app-user-account-detail',
  templateUrl: './user-account-detail.component.html',
  styleUrls: ['./user-account-detail.component.css', './user-account-detail.form,component.css']
})
export class UserAccountDetailComponent implements OnInit {

 showEditForm: boolean = false;
 firstName: string='myFirstName';
 email:string;

 profileUpdateForm : FormGroup;

 userDetail:UserDetailInterface={ own:false, friend:false, friendRequestSentByThisUser:false, friendRequestSentToThisUser:false, email: '', firstName: '', lastName: ''
 };

 constructor(fb: FormBuilder, public authGuardService: AuthGuardService, private route: ActivatedRoute, private router: Router, public httpClientService:HttpClientService) {
  this.profileUpdateForm = fb.group({
    'file' : [''],
    'firstName': [''],
    'lastName': [''],
    'email': [''],
    'phone': [''],
    'password':[''],
    'gender': [''],
    'dob':[''],
    'address': [''],
    'personalInfo': [''],
    'academicInfo': [''],
    'jobInfo': [''],
  })


  if(this.router.url=="/"){
    this.email=this.authGuardService.userDetail.email;
  }else if(this.route.snapshot.params['email']){
    this.email=this.route.snapshot.params['email'];
  }else{
    this.email=this.authGuardService.userDetail.email;
  }
  console.log('constructor '+this.email);
  httpClientService.showUserProfile(this.email).subscribe(
    (response) => {
      this.userDetail=JSON.parse(response);
      this.profileUpdateForm.patchValue({firstName: this.userDetail.firstName});
      this.profileUpdateForm.patchValue({lastName: this.userDetail.lastName});
      this.profileUpdateForm.patchValue({email: this.userDetail.email});
      this.profileUpdateForm.patchValue({phone: this.userDetail.phone});
      this.profileUpdateForm.patchValue({password: this.authGuardService.password});
      this.profileUpdateForm.patchValue({gender: this.userDetail.gender});
      this.profileUpdateForm.patchValue({dob: this.userDetail.dob});
      this.profileUpdateForm.patchValue({address: this.userDetail.address});
      this.profileUpdateForm.patchValue({personalInfo: this.userDetail.personalInfo});
      this.profileUpdateForm.patchValue({academicInfo: this.userDetail.academicInfo});
      this.profileUpdateForm.patchValue({jobInfo: this.userDetail.jobInfo});
    },
    (error) => {
      console.error(error.message);
    }
  )
  }

  ngOnInit(): void {
  }


  editForm(){

  }

  onFileSelect(event:any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.profileUpdateForm.get('file')?.setValue(file);

    }
  }


  updateUserProfile(data:any){
    const formData = new FormData();
    formData.append('file', this.profileUpdateForm.get('file')?.value);
    formData.append('firstName',this.profileUpdateForm.get('firstName')?.value);
    formData.append('lastName',this.profileUpdateForm.get('lastName')?.value);
    formData.append('email',this.profileUpdateForm.get('email')?.value);
    formData.append('phone',this.profileUpdateForm.get('phone')?.value);
    formData.append('password',this.profileUpdateForm.get('password')?.value);
    formData.append('gender',this.profileUpdateForm.get('gender')?.value);
    formData.append('dob',this.profileUpdateForm.get('dob')?.value);
    formData.append('address',this.profileUpdateForm.get('address')?.value);
    formData.append('personalInfo',this.profileUpdateForm.get('personalInfo')?.value);
    formData.append('academicInfo',this.profileUpdateForm.get('academicInfo')?.value);
    formData.append('jobInfo',this.profileUpdateForm.get('jobInfo')?.value);
    
    this.httpClientService.updateUserProfile(formData).subscribe( 
      (response) => {
        console.log(response);
        this.userDetail = JSON.parse(response);
        this.showEditForm=!this.showEditForm;
      },
      (error) => {
        console.error(error.message);
      }

    )


  }

  deleteAccount(){
    this.httpClientService.deleteUser().subscribe(
      (response) => {
        console.log(response);
        this.authGuardService.jwtKeyWithBearer='';
        this.authGuardService.isLoggedIn=false;
      },
      (error) => {
        console.error(error.message);
      }

    )
  }


}
