import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams  } from '@angular/common/http';
import { AuthGuardService } from "./auth-guard.service";



@Injectable({
  providedIn: 'root'
})
export class HttpClientService {


  ROOT_URI = "http://phasebook-env.eba-7nzhqfz3.us-east-2.elasticbeanstalk.com";

  constructor(private httpClient: HttpClient, private authGuardService: AuthGuardService) { }


  public generateToken(request: any) {
    return this.httpClient.post<string>(this.ROOT_URI+"/client/login", request, {  responseType: 'text' as 'json' });
  }

  public signup(request: any) {
    return this.httpClient.post<string>(this.ROOT_URI+"/client/signup", request, {  responseType: 'text' as 'json' });
  }

  public sendChat(request: any) {
    return this.httpClient.post<string>(this.ROOT_URI+"/test/sendChat", request, {  responseType: 'text' as 'json' });
  }

  public uploadPost(request: any) {
    let tokenStr = this.authGuardService.jwtKeyWithBearer;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.post<string>(this.ROOT_URI+"/client/uploadPost", request, {headers,  responseType: 'text' as 'json' });
 }


  public requestPosts(request: any) {
     let tokenStr = this.authGuardService.jwtKeyWithBearer;
     const headers = new HttpHeaders().set('Authorization', tokenStr);
     return this.httpClient.post<string>(this.ROOT_URI+"/client/posts", request, {headers,  responseType: 'text' as 'json' });
  }

  public deletePost(postId: string) {
    let tokenStr = this.authGuardService.jwtKeyWithBearer;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    let params = new HttpParams()
    .set('postId', postId);
    // .set('sort', 'name');
    return this.httpClient.post<string>(this.ROOT_URI+"/client/deletePost", {}, {params, headers,  responseType: 'text' as 'json' });
 }

 public updatePostAudience( audience:string, postId: number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('postId', postId)
  .set('audience', audience);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/updatePost", {}, {params, headers,  responseType: 'text' as 'json' });
}

public likeOrDislikeOrDetachPost( like:boolean, nullVal: boolean, postId:number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('like', like)
  .set('null', nullVal)
  .set('postId', postId);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/like", {}, {params, headers,  responseType: 'text' as 'json' });
}

public showComments( postId:number, pageNumber: number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('postId', postId)
  .set('pageNumber', pageNumber);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/comments", {}, {params, headers,  responseType: 'text' as 'json' });
}

public showUserProfile(email:string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  // let body={
  //   'postId':postId.toString(),
  //   'content':comment
  // }
// let body = new FormData();
// body.append('PostId', postId.toString());
// body.append('content', comment);
let params = new HttpParams()
.set('email', email);

  return this.httpClient.post<string>(this.ROOT_URI+"/client/userProfile", {}, {params, headers,  responseType: 'text' as 'json' });
}
public submitComment2(data:any) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/submitComment", data, {headers,  responseType: 'text' as 'json' });
}

public updateUserProfile(request: any) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/updateUserProfile", request, {headers,  responseType: 'text' as 'json' });
}

public deleteUser() {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/deleteUserAccount", {}, {headers,  responseType: 'text' as 'json' });
}

////////////////////////////////////start///////////////////////
public friends(pageNumber: number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('pageNumber', pageNumber);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/friends", {}, {params, headers,  responseType: 'text' as 'json' });
}
public allFriends() {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/allFriends", {}, {headers,  responseType: 'text' as 'json' });
}

public friendRequestsSent(pageNumber: number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('pageNumber', pageNumber);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/friendRequestsSent", {}, {params, headers,  responseType: 'text' as 'json' });
}

public friendRequestsReceived(pageNumber: number) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('pageNumber', pageNumber);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/friendRequestsReceived", {}, {params, headers,  responseType: 'text' as 'json' });
}
public sendFriendRequest(email: string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('email', email);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/sendFriendRequest", {}, {params, headers,  responseType: 'text' as 'json' });
}
public cancelSentFriendRequest(email: string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('email', email);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/cancelSentFriendRequest", {}, {params, headers,  responseType: 'text' as 'json' });
}
public acceptFriendRequest(email: string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('email', email);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/acceptFriendRequest", {}, {params, headers,  responseType: 'text' as 'json' });
}
public unFriend(email: string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('email', email);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/unFriend", {}, {params, headers,  responseType: 'text' as 'json' });
}
public people(pageNumber: number, keyword:string) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('pageNumber', pageNumber)
  .set('keyword', keyword);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/people", {}, {params, headers,  responseType: 'text' as 'json' });
}

public communicate(request: any) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/communicate", request, {headers,  responseType: 'text' as 'json' });
}

public unReadMessages() {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/unreadMessage", {}, {headers,  responseType: 'text' as 'json' });
}

public sendMessageReceivedConformation(data:number[]) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  const formData = new FormData();
  
  let a:number[]=[];
  a.push(1);
  a.push(2);
  a.push(3);
  a.push(4);
  a.push(5);

  return this.httpClient.post<string>(this.ROOT_URI+"/client/messageReceivedConformation", data, {headers,  responseType: 'text' as 'json' });
}

public sendMessageReadConformation(data:number[]) {
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  const formData = new FormData();
  
  let a:number[]=[];
  a.push(1);
  a.push(2);
  a.push(3);
  a.push(4);
  a.push(5);

  return this.httpClient.post<string>(this.ROOT_URI+"/client/messageReadConformation", data, {headers,  responseType: 'text' as 'json' });
}

public requestMessageWithFriendAndId(friendEamil:string, earliestChatId:number){
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('friendEamil', friendEamil)
  .set('earliestChatId', earliestChatId);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/previousChat", {}, {params, headers,  responseType: 'text' as 'json' });


}
public requestMessageWithFriend(friendEamil:string){
  let tokenStr = this.authGuardService.jwtKeyWithBearer;
  const headers = new HttpHeaders().set('Authorization', tokenStr);
  let params = new HttpParams()
  .set('friendEamil', friendEamil);
  return this.httpClient.post<string>(this.ROOT_URI+"/client/previousChatsWithoutId", {}, {params, headers,  responseType: 'text' as 'json' });
}

}
