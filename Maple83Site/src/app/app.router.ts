import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';

const appRoutes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: AppComponent
    }
];

export const Routing = RouterModule.forRoot(appRoutes);
