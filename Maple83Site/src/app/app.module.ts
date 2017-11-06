import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { NewsService } from '../services/news.service';
import { AppComponent } from './app.component';

import { HttpModule } from '@angular/http';

import { Routing } from './app.router';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    Routing
  ],
  providers: [NewsService],
  bootstrap: [AppComponent]

})
export class AppModule { }
