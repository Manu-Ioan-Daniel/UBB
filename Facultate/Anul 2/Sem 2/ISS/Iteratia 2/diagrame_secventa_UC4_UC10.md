## UC-4: Submit Purchase Inquiry

```mermaid
sequenceDiagram
    participant U as «actor» User
    participant B as «boundary» MotorcycleDetailPage
    participant C as «control» MotorcyclePageController
    participant S as «entity» SGBD

    U->>B: 1: clickSendInquiry()
    B->>C: 2: <<create>>
    C-->>B: 3: displayInquiryForm(motorcycleName, userContact)
    B-->>U: 4: showInquiryForm()
    U->>B: 5: submitInquiry(contact, financing, message)
    B->>C: 6: validateAndSave(data)
    C->>S: 7: saveInquiry(data)
    S-->>C: 8: inquirySaved(referenceNo)
    C-->>C: 9: <<destroy>>
    B-->>U: 10: showConfirmation("Inquiry submitted. Dealer will contact you shortly.")
```

---

## UC-5: Add Motorcycle Listing

```mermaid
sequenceDiagram
    participant D as «actor» Dealer
    participant B as «boundary» AddListingForm
    participant C as «control» MotorcycleListingController
    participant S as «entity» SGBD

    D->>B: 1: clickAddNewListing()
    B->>C: 2: <<create>>
    C-->>B: 3: displayListingForm()
    B-->>D: 4: showListingForm()
    D->>B: 5: fillForm(brand, model, year, price, photos, ...)
    B->>C: 6: validateAndSubmit(data)
    C->>C: 7: validateFields(data)
    C->>S: 8: <<create>> saveListing(data)
    S-->>C: 9: listingSaved
    C-->>B: 10: submissionSuccess
    destroy C
    C-->>C: 11: <<destroy>>
    B-->>D: 12: redirectToMyListings()
```

---

## UC-6: Edit Motorcycle Listing

```mermaid
sequenceDiagram
    participant D as «actor» Dealer
    participant B as «boundary» EditListingForm
    participant C as «control» MotorcycleListingController
    participant S as «entity» SGBD

    D->>B: 1: clickEdit(listingId)
    B->>C: 2: <<create>>
    C->>S: 3: fetchListing(listingId)
    S-->>C: 4: listingData
    C-->>B: 5: prefillForm(listingData)
    B-->>D: 6: showEditForm()
    D->>B: 7: modifyFields(updatedData)
    B->>C: 8: validateAndUpdate(updatedData)
    C->>C: 9: validateFields(updatedData)
    C->>S: 10: updateListing(listingId, updatedData)
    S-->>C: 11: listingUpdated
    C-->>B: 12: updateSuccess
    destroy C
    C-->>C: 13: <<destroy>>
    B-->>D: 14: redirectToMyListings()
```

---

## UC-7: Mark Listing as Sold

```mermaid
sequenceDiagram
    participant D as «actor» Dealer
    participant B as «boundary» MyListingsPage
    participant C as «control» ListingCtrl
    participant S as «entity» SGBD

    D->>B: 1: clickMarkAsSold(listingId)
    B->>C: 2: <<create>>
    C-->>B: 3: displayConfirmation("Are you sure?")
    B-->>D: 4: showConfirmationDialog()
    D->>B: 5: confirm()
    B->>C: 6: markAsSold(listingId)
    C->>S: 7: checkPendingAppointments(listingId)
    S-->>C: 8: noPendingAppointments
    C->>S: 9: updateListingStatus(listingId, "SOLD")
    S-->>C: 10: statusUpdated
    C-->>B: 11: soldSuccess
    destroy C
    C-->>C: 12: <<destroy>>
    B-->>D: 13: showMessage("Listing removed from public catalog.")
```

---

## UC-8: Schedule a Test Ride

```mermaid
sequenceDiagram
    participant U as «actor» User
    participant B as «boundary» MotorcycleDetailPage
    participant C as «control» TestRideController
    participant D as «actor» Dealer
    participant S as «entity» SGBD

    U->>B: 1: clickScheduleTestRide()
    B->>C: 2: <<create>>
    C->>S: 3: fetchAvailableSlots(dealerId)
    S-->>C: 4: availableSlots
    C-->>B: 5: displaySlots(availableSlots)
    B-->>U: 6: showCalendar()
    U->>B: 7: selectSlot(date, time)
    U->>B: 8: submitBooking(contact, note)
    B->>C: 9: saveAppointment(data)
    C->>S: 10: <<create>> saveAppointment(data)
    C-->>B: 11: bookingConfirmed
    destroy C
    C-->>C: 12: <<destroy>>
    B-->>U: 13: showConfirmation(appointmentDetails)
```

---

## UC-9: Approve Dealer Registration

```mermaid
sequenceDiagram
    participant A as «actor» Admin
    participant B as «boundary» ManageDealerRegistrations
    participant C as «control» DealerController
    participant D as «actor» User
    participant S as «entity» SGBD

    A->>B: 1: openPendingRegistrations()
    B->>C: 2: <<create>>
    C->>S: 3: fetchPendingDealers()
    S-->>C: 4: pendingList
    C-->>B: 5: displayPendingList(pendingList)
    B-->>A: 6: showDealerList()
    A->>B: 13: clickApprove(dealerId)
    B->>C: 14: approveDealer(dealerId)
    C->>S: 15: updateDealerStatus(dealerId, "APPROVED")
    C->>S: 16: assignRole(dealerId, "DEALER")
    S-->>C: 17: dealerActivated
    C-->>B: 18: approvalSuccess
    destroy C
    C-->>C: 19: <<destroy>>
    B-->>A: 20: showUpdatedList()
```

---

## UC-10: Suspend Account

```mermaid
sequenceDiagram
    participant A as «actor» Admin
    participant B as «boundary» UserManagementPage
    participant C as «control» UserController
    participant T as «actor» TargetUser
    participant S as «entity» SGBD

    A->>B: 1: searchAndSelectAccount(userId)
    B->>C: 2: <<create>>
    C->>S: 3: fetchAccountDetails(userId)
    S-->>C: 4: accountDetails
    C-->>B: 5: displayAccountDetails()
    B-->>A: 6: showAccountDetails()
    A->>B: 7: clickSuspendAccount(userId)
    B->>C: 12: suspendAccount(userId)
    C->>S: 13: updateStatus(userId, "SUSPENDED")
    C->>S: 14: terminateActiveSessions(userId)
    S-->>C: 15: accountSuspended
    C-->>B: 16: suspensionSuccess
    destroy C
    C-->>C: 17: <<destroy>>
    B-->>A: 18: showUpdatedUserList()
```
