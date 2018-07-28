import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

/*this script is the one bootstrapping the main angular module (app.module) in
 the app-root selector of the index.html entry page
 */
platformBrowserDynamic().bootstrapModule(AppModule).catch(err => console.log(err));
