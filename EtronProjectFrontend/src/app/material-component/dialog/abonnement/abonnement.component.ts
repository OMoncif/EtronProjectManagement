import { Component, EventEmitter, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../category/category.component';
import { AbonnementService } from 'src/app/services/abonnement.service';

@Component({
  selector: 'app-abonnement',
  templateUrl: './abonnement.component.html',
  styleUrls: ['./abonnement.component.scss']
})
export class AbonnementComponent {
  onAddAbonnement = new EventEmitter();
  onEditAbonnement = new EventEmitter();
  planForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";

  responseMessage:any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private abonnementService:AbonnementService,
  public dialogRef: MatDialogRef<CategoryComponent>,
  private snackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.planForm = this.formBuilder.group({
      type:[null,[Validators.required]],
      fraismois:[null,[Validators.required]],
      dureeContrat:[null,[Validators.required]],
      fraisAC:[null,[Validators.required]],
      fraisDCHPC:[null,[Validators.required]],
      fraisHautePuissance:[null,[Validators.required]],
      fraisBlocageAC:[null,[Validators.required]],
      fraisBlocageDC:[null,[Validators.required]]
    });
    if (this.dialogData.action === 'Edit'){
      this.dialogAction = "Edit";
      this.action = "Update"
      this.planForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    }
    else {
      this.add();
    }
  }

  add() {
    var formData = this.planForm.value;
    var data = {
      type: formData.type,
      fraismois: formData.fraismois,
      dureeContrat: formData.dureeContrat,
      fraisAC: formData.fraisAC,
      fraisDCHPC: formData.fraisDCHPC,
      fraisHautePuissance: formData.fraisHautePuissance,
      fraisBlocageAC: formData.fraisBlocageAC,
      fraisBlocageDC: formData.fraisBlocageDC
    }
    this.abonnementService.add(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddAbonnement.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error)=>{
      this.dialogRef.close();
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    });
  }

  edit() {
    var formData = this.planForm.value;
    var data = {
      id: this.dialogData.data.id,
      type: formData.type,
      fraismois: formData.fraismois,
      dureeContrat: formData.dureeContrat,
      fraisAC: formData.fraisAC,
      fraisDCHPC: formData.fraisDCHPC,
      fraisHautePuissance: formData.fraisHautePuissance,
      fraisBlocageAC: formData.fraisBlocageAC,
      fraisBlocageDC: formData.fraisBlocageDC
    }
    this.abonnementService.update(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddAbonnement.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error)=>{
      this.dialogRef.close();
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    });
  }
}
