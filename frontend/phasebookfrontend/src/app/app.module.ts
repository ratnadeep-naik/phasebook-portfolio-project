import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router'
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { PostComponent } from './post/post.component';
import {loginInfoService} from './services/loginInfo';
import { PostContainerComponent } from './post-container/post-container.component';
import { UserAccountContainerComponent } from './user-account-container/user-account-container.component';
import { UserAccountDetailComponent } from './user-account-detail/user-account-detail.component';
import { UserListContainerComponent } from './user-list-container/user-list-container.component';
import { UserListElementComponent } from './user-list-element/user-list-element.component';
import { UserChatContainerComponent } from './user-chat-container/user-chat-container.component';
import { GroupAccountContainerComponent } from './group-account-container/group-account-container.component';
import { GroupAccountDetailComponent } from './group-account-detail/group-account-detail.component';
import { GroupListContainerComponent } from './group-list-container/group-list-container.component';
import { GroupListElementComponent } from './group-list-element/group-list-element.component';
import { GroupChatContainerComponent } from './group-chat-container/group-chat-container.component';
import { ExperimentComponent } from './experiment/experiment.component';
import { WebsocketExperimentComponent } from './websocket-experiment/websocket-experiment.component';

import {AuthGuardService} from './auth-guard.service';
import {AuthGuard} from './auth.guard';
import { HttpClientService } from "./http-client.service";
import { WebSocketAPIService } from "./web-socket-api.service";
import { WebsocketExperiment2Component } from './websocket-experiment2/websocket-experiment2.component';
import { WebsocketExperiment3Component } from './websocket-experiment3/websocket-experiment3.component';


const routes: Routes = [
//   { path: '', redirectTo: 'home', pathMatch: 'full' },
   { path: 'fsddfds', redirectTo: '' },
  // { path: '', component: LoginComponent },
   { path: 'friend', component: LoginComponent},
   { path: 'group', component: LoginComponent },
   { path: 'timeline', component: LoginComponent },

   { path: 'friends', component: LoginComponent },
   { path: 'friendRequestsSent', component: LoginComponent },
   { path: 'friendRequestsReceived', component: LoginComponent },
   { path: 'userCreatedGroups', component: LoginComponent },
   { path: 'userJoinedGroups', component: LoginComponent },
   { path: 'groupRequestsSent', component: LoginComponent },
   { path: 'groupRequestsReceived', component: LoginComponent },


]
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    PostComponent,
    PostContainerComponent,
    UserAccountContainerComponent,
    UserAccountDetailComponent,
    UserListContainerComponent,
    UserListElementComponent,
    UserChatContainerComponent,
    GroupAccountContainerComponent,
    GroupAccountDetailComponent,
    GroupListContainerComponent,
    GroupListElementComponent,
    GroupChatContainerComponent,
    ExperimentComponent,
    WebsocketExperimentComponent,
    WebsocketExperiment2Component,
    WebsocketExperiment3Component,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
//    RouterModule.forRoot(routes),
    InfiniteScrollModule,

  ],
  providers: [loginInfoService, AuthGuardService, AuthGuard, HttpClientService, WebSocketAPIService],
  bootstrap: [AppComponent]
})
export class AppModule { }
