import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ChiPhiComponent } from './list/chi-phi.component';
import { ChiPhiDetailComponent } from './detail/chi-phi-detail.component';
import { ChiPhiUpdateComponent } from './update/chi-phi-update.component';
import { ChiPhiDeleteDialogComponent } from './delete/chi-phi-delete-dialog.component';
import { ChiPhiRoutingModule } from './route/chi-phi-routing.module';

@NgModule({
  imports: [SharedModule, ChiPhiRoutingModule],
  declarations: [ChiPhiComponent, ChiPhiDetailComponent, ChiPhiUpdateComponent, ChiPhiDeleteDialogComponent],
  entryComponents: [ChiPhiDeleteDialogComponent],
})
export class ChiPhiModule {}
