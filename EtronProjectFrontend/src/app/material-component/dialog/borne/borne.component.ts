import { Component, EventEmitter, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../category/category.component';
import { BorneService } from 'src/app/services/borne.service';

@Component({
  selector: 'app-borne',
  templateUrl: './borne.component.html',
  styleUrls: ['./borne.component.scss']
})
export class BorneComponent {
  onAddBorne = new EventEmitter();
  onEditBorne = new EventEmitter();
  borneForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";

  responseMessage:any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private borneService:BorneService,
  public dialogRef: MatDialogRef<CategoryComponent>,
  private snackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.borneForm = this.formBuilder.group({
      typecharge:[null,[Validators.required]],
      latitude:[null,[Validators.required]],
      longitude:[null,[Validators.required]],
      disponible:[null,[Validators.required]]
    });
    if (this.dialogData.action === 'Edit'){
      this.dialogAction = "Edit";
      this.action = "Update"
      this.borneForm.patchValue(this.dialogData.data);
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
    var formData = this.borneForm.value;
    var data = {
      typecharge: formData.typecharge,
      latitude: formData.latitude,
      longitude: formData.longitude,
      disponible: formData.disponible
      
    }
    this.borneService.add(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddBorne.emit();
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
    var formData = this.borneForm.value;
    var data = {
      typecharge: formData.typecharge,
      latitude: formData.latitude,
      longitude: formData.longitude,
      disponible: formData.disponible
    }
    this.borneService.update(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddBorne.emit();
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
