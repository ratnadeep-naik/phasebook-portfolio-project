import { Component } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';

export interface Credentials {
  username: string,
  password: string,
}


export class UserCredentials {

  constructor(
      public username: string,
      public password: string,
  ) { }   
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

//  private options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
//private options = { headers: new HttpHeaders().set('Content-Type', 'multipart/form-data') };
private options = { headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded') };


  
  constructor(private http: HttpClient){

  }

  sendRequest(){
    this.http
      .get(
        'http://localhost:8080/test/allusers'
      )
      .subscribe(responseData => {
        // console.log(responseData);
        console.log("it works: "+responseData);
      });

  }


login(){
  
}
  
  loginUser(){
    const bar: Credentials = { username: "user1@gmail.com", password: "youcancallmex" };

    let params = new HttpParams();
// params = params.append('username', 'user1@gmail.com');
// params = params.append('password', 'youcancallmex');
params = params.set('username', 'user1@gmail.com');
params = params.set('password', 'youcancallmex');

//const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

const headers = new HttpHeaders();
headers.set('Content-Type', 'application/x-www-form-urlencoded');
//headers.set('charset', 'utf-8');

    this.http
      .post<any>(
        'http://localhost:8080/login',
        params,
        { headers, responseType: 'text' as 'json' }
                
      )
      .subscribe(responseData => {
        console.log("it works: "+responseData);
      });


  }


  sendRequestPost(){
    const formData = new FormData();
    formData.append("username", "user1@gmail.com");
    formData.append("password", "youcancallmex");
    console.log(formData);

    const bar: Credentials = { username: "ratnadeep", password: "hello" };

    let params = new HttpParams();
params = params.append('username', 'user1@gmail.com');
params = params.append('password', 'youcancallmex');

    this.http
      .post<any>(
        'http://localhost:8080/test/allusersPost',
        params,
        this.options
                
      )
      .subscribe(responseData => {
        console.log("it works: "+responseData);
      });

  }


  sendLoginRequest(){

    // const formData = new FormData();
    // formData.append("username", "user1@gmail.com");
    // formData.append("password", "youcancallmex");
    // console.log(formData);
    postData:  { username: 'user1@gmail.com'; password: 'youcancallmex'};

    JSON.parse(JSON.stringify(new UserCredentials('user1@gmail.com','youcancallmex')));

    console.log(JSON.stringify(new UserCredentials('user1@gmail.com','youcancallmex')));
    console.log(JSON.parse(JSON.stringify(new UserCredentials('user1@gmail.com','youcancallmex'))));

    let urlSearchParams = new URLSearchParams();
    urlSearchParams.append('username', 'user1@gmail.com' );
    urlSearchParams.append('password', 'youcancallmex' );

    this.http
    .post(
      'http://localhost:8080/login',
//      formData,
//          JSON.stringify(new UserCredentials('user1@gmail.com','youcancallmex')),
//          { username: 'user1@gmail.com', password: 'youcancallmex'},
//JSON.parse(JSON.stringify(new UserCredentials('user1@gmail.com','youcancallmex'))),
urlSearchParams,
      { responseType: 'text'}
    )
    .subscribe(responseData => {
      console.log(responseData);
    }
    );
  }

}
