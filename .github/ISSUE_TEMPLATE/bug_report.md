name: "Bug Report"
description: Report Bug To Help Improvements.
title: "‎ "
labels: [
  "bug"
]
body:
  - type: textarea
    id: description
    attributes:
      label: "Bug Submission"
      description: Please Explain the bug which you exprienced in detail
      placeholder: Optionally you can Attach screenshots , video , etc...
    validations:
      required: true
  - type: dropdown
    id: osh
    attributes:
      label: "Android Version"
      description: Please Select Your Android Version
      multiple: true
      options:
        - Any Android
        - Android 6
        - Android 7
        - Android 8
        - Android 9
        - Android 10
        - Android 11
        - Android 12
        - Android 13
        - Android 14
        - Android 15
    validations:
      required: true
  - type: textarea
    id: reprod
    attributes:
      label: "Comments or Extra Info"
      description: Please enter Comments or Other information which you want to tell
      value: 
      render: bash
    validations:
      required: false
  - type: dropdown
    id: os
    attributes:
      label: "App Version"
      description: 
      multiple: true
      options:
        - Latest v3.6
        - v3.5
    validations:
      required: false